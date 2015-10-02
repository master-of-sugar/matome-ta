package com.github.master_of_sugar.matome_ta.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pager {
	
	public static final int LIMIT_LIST_COUNT = 10;
	
	public static class Page{
		private int number;
		private boolean active;
		
		public Page(int number,int currentPage) {
			this.number = number;
			this.active = number == currentPage;
		}
		
		public int getNumber() {
			return number;
		}
		public boolean isActive() {
			return active;
		}
		
	}
	
	private int currentPage;
	private List<Page> pages;
	
	public Pager(int currentPage,long maxElements) {
		this.currentPage = currentPage;
		int maxPage = 1;
		if(maxElements != 0 && maxElements >= LIMIT_LIST_COUNT){
			maxPage = (int)maxElements/LIMIT_LIST_COUNT;
			if(maxElements%LIMIT_LIST_COUNT != 0){
				maxPage++;
			}
		}
		
		this.pages = IntStream.range(1, maxPage+1).mapToObj((i) -> {
			return new Page(i, currentPage);
		}).collect(Collectors.toList());
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public boolean hasPrevPage(){
		return currentPage != 1;
	}
	
	public int prevPage(){
		return hasPrevPage()?currentPage-1:currentPage;
	}
	
	public boolean hasNextPage(){
		return currentPage != getPages().size();
	}
	
	public int nextPage(){
		return hasNextPage()?currentPage+1:currentPage;
	}
	
	public List<Page> getPages() {
		return pages;
	}
}
