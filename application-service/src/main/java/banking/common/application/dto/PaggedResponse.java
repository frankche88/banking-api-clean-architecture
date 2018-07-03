package banking.common.application.dto;

import java.util.List;

public class PaggedResponse<T> {
	
	private List<T> content;

	private int currentPage;

	private int pageSize;

	private long totalRecords;
	
	

	public PaggedResponse(List<T> content, int currentPage, int pageSize, long totalRecords) {
		super();
		this.content = content;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
	}

	public List<T> getContent() {
		return content;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public long getTotalPages() {
		return (totalRecords + pageSize -1) /pageSize;
	}
	
	

}
