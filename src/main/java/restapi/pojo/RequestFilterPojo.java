package restapi.pojo;

/**
 * @author Максим Зеленский
 */
public class RequestFilterPojo {
    private String author;
    private Integer offset;
    private Integer limit;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
