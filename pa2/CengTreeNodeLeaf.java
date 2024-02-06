import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);
        // TODO: Extra initializations
        books = new ArrayList<CengBook>();
        this.type = CengNodeType.Leaf;
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions

    public void addBook (CengBook book) {
        int bookId = book.getBookID();
        int size = this.books.size();
        int i = 0;
        for (; i < size; i++) {
            if (this.books.get(i).getBookID() > bookId) {
                break;
            }
        }
        this.books.add(i, book);
    }

    public ArrayList<CengBook> getAllBooks(){
        return this.books;
    }

    public void setBooks(ArrayList<CengBook> newBooks){
        this.books = newBooks;
    }

    public CengBook getBook(int bookID){
        for (int i = 0; i < this.bookCount(); i++){
            if (books.get(i).getBookID().equals(bookID)){
                return books.get(i);
            }
        }
        return null;
    }
}
