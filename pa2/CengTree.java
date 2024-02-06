import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);
    }

    public void addBook(CengBook book)
    {
        // TODO: Insert Book to Tree
        CengTreeNodeLeaf leaf = findTheLeaf(book.getBookID());
        CengTreeNodeInternal node = new CengTreeNodeInternal(null);
        leaf.addBook(book);
        if (leaf.bookCount() > 2*CengTreeNode.order) {
            node = copyUp(leaf);
            while (node.keyCount() > 2*CengTreeNode.order){
                node = pushUp(node);
            }
            if (node.getParent() == null) this.root = node;
        }
    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        boolean found = false;
        ArrayList<CengTreeNode> result = new ArrayList<CengTreeNode>();
        if (this.root.getType() == CengNodeType.Leaf){
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) this.root;
            int size = leaf.bookCount();
            for (int i = 0; i < size; i++){
                if (leaf.getAllBooks().get(i).getBookID().equals(bookID)){
                    result.add(this.root);
                    found = true;
                    break;
                }
            }
        }
        else {
            CengTreeNode node = (CengTreeNodeInternal) this.root;
            while (node.getType() == CengNodeType.Internal){
                result.add(node);
                int index = ((CengTreeNodeInternal)node).getPosition(bookID);
                ArrayList<CengTreeNode> children = ((CengTreeNodeInternal)node).getAllChildren();
                node = children.get(index);
            }
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            int size = leaf.bookCount();
            for (int i = 0; i < size; i++){
                if (leaf.getAllBooks().get(i).getBookID().equals(bookID)){
                    result.add(leaf);
                    found = true;
                    break;
                }
            }
        }
        if (found){
            String tabs;
            int size = result.size();
            for (int i = 0; i < size - 1; i++){
                CengTreeNodeInternal node = (CengTreeNodeInternal) result.get(i);
                int keyNum = node.keyCount();

                tabs = "\t\t".repeat(node.level -1);
                System.out.println(tabs + "<index>");
                for (int j = 0; j < keyNum; j++){
                    int key = node.keyAtIndex(j);
                    System.out.println(tabs + key);
                }
                System.out.println(tabs + "</index>");
            }
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) result.get(size-1);
            CengBook book = leaf.getBook(bookID);
            tabs = "\t\t".repeat(leaf.level -1);
            System.out.println(tabs + "<record>" + book.fullName() + "</record>");
            return result;

        }
        else {
            System.out.println("Could not find " + bookID + ".");
            return null;
        }
    }

    public void printTree()
    {
        // TODO: Print the whole tree to console
        print(this.root);

    }

    // Any extra functions...
    public CengTreeNodeLeaf findTheLeaf (int bookID){
        if (this.root.getType() == CengNodeType.Leaf){
            return (CengTreeNodeLeaf) this.root;
        }
        CengTreeNodeInternal node = (CengTreeNodeInternal) this.root;
        while (true){
            int index = node.getPosition(bookID);
            ArrayList<CengTreeNode> children = node.getAllChildren();
            if (children.get(index).getType() == CengNodeType.Leaf){
                return (CengTreeNodeLeaf) children.get(index);
            }
            else {
                node = (CengTreeNodeInternal) children.get(index);
            }
        }
    }
    public CengTreeNodeInternal copyUp(CengTreeNodeLeaf node){
        CengTreeNodeInternal parent = (CengTreeNodeInternal) node.getParent();
        ArrayList<CengBook> allBooks = node.getAllBooks();
        ArrayList<CengBook> leftBooks = new ArrayList<CengBook>();
        ArrayList<CengBook> rightBooks = new ArrayList<CengBook>();
        int key = 0;
        for (int i=0; i < allBooks.size(); i++){
            if (i < CengTreeNode.order){ //leftbooks
                leftBooks.add(allBooks.get(i));
            }
            else if (i == CengTreeNode.order){
                key = allBooks.get(i).getBookID();
                rightBooks.add(allBooks.get(i));
            }
            else { //rightbooks
                rightBooks.add(allBooks.get(i));
            }
        }
        CengTreeNodeLeaf leftLeaf = new CengTreeNodeLeaf(parent); //add to parents children
        CengTreeNodeLeaf rightLeaf = new CengTreeNodeLeaf(parent); //add to parents children
        leftLeaf.setBooks(leftBooks);
        rightLeaf.setBooks(rightBooks);

        if (parent == null){ //node = root;
            parent = new CengTreeNodeInternal(null); //new root
            leftLeaf.setParent(parent);
            rightLeaf.setParent(parent);
            parent.addChildren(0, key, leftLeaf, rightLeaf, false);
        }

        else {
            int pos = parent.getAllChildren().indexOf(node);
            parent.addChildren(pos, key, leftLeaf, rightLeaf, true);
        }
        return parent;
    }
    public CengTreeNodeInternal pushUp(CengTreeNodeInternal node){
        CengTreeNodeInternal parent = (CengTreeNodeInternal) node.getParent();
        CengTreeNodeInternal leftNode = new CengTreeNodeInternal(parent);
        CengTreeNodeInternal rightNode = new CengTreeNodeInternal(parent);
        int size = node.keyCount();
        int key = 0;
        int i = 0;
        for (; i < size; i++){
            CengTreeNode temp = node.getChild(i);
            int temp_key = node.keyAtIndex(i);
            if (i < CengTreeNode.order){
                temp.setParent(leftNode);
                leftNode.addOnlyChild(temp_key, temp);
            }
            else if (i == CengTreeNode.order){ //middle node must be pushed up
                key = temp_key;
                temp.setParent(leftNode);
                leftNode.addChildWithoutKey(temp);
            }
            else {
                temp.setParent(rightNode);
                rightNode.addOnlyChild(temp_key,temp);
            }
        }
        CengTreeNode temp = node.getChild(size);
        rightNode.addChildWithoutKey(temp);
        temp.setParent(rightNode);

        if (parent == null){ //node = root;
            parent = new CengTreeNodeInternal(null); //new root
            leftNode.setParent(parent);
            rightNode.setParent(parent);
            parent.addChildren(0, key, leftNode, rightNode, false);
        }
        else {
            int pos = parent.getAllChildren().indexOf(node);
            parent.addChildren(pos, key, leftNode, rightNode, true);
        }

        return parent;
    }

    public void print(CengTreeNode node){
        String tabs = "\t\t".repeat(node.level - 1);
        if (node.getType() == CengNodeType.Leaf){
            System.out.println(tabs + "<data>");
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            ArrayList<CengBook> books = leaf.getAllBooks();
            for (int i = 0; i < leaf.bookCount(); i++){
                CengBook book = books.get(i);
                System.out.println(tabs + "<record>" + book.fullName() + "</record>");
            }
            System.out.println(tabs + "</data>");
        }
        else {
            System.out.println(tabs + "<index>");
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            int keyNum = internal.keyCount();
            for (int j = 0; j < keyNum; j++){
                int key = internal.keyAtIndex(j);
                System.out.println(tabs + key);
            }
            System.out.println(tabs + "</index>");
            for (CengTreeNode child: internal.getAllChildren()){
                print(child);
            }
        }
    }
}
