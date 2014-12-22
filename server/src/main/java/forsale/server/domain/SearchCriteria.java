package forsale.server.domain;

final public class SearchCriteria {

    private final String query;

    public SearchCriteria(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
