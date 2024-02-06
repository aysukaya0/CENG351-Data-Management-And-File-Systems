import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);
        // TODO: Extra initializations, if necessary.
        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions

    public int getPosition(int bookID) {
        int size = this.keys.size();
        int i = 0;
        while (i < size){
            if (bookID >= this.keys.get(i)){
                i++;
            }
            else break;
        }
        return i;
    }

    public void addChildren(int pos, int key, CengTreeNode leftNode, CengTreeNode rightNode, boolean flag){
        if (!flag){
            this.keys.add(key);
            this.children.add(leftNode);
            this.children.add(rightNode);
        }
        else {
            this.keys.add(pos, key);
            this.children.set(pos, leftNode);
            this.children.add(pos+1, rightNode);
        }
    }

    public void addOnlyChild(int key, CengTreeNode node){
        this.keys.add(key);
        this.children.add(node);
    }
    public CengTreeNode getChild(int i){
        return this.children.get(i);
    }

    public void addChildWithoutKey(CengTreeNode node){
        this.children.add(node);
    }

}
