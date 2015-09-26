package com.github.master_of_sugar.matome_ta;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleExceptionMapper implements ExceptionMapper<Exception>{

	private static Logger logger = LoggerFactory.getLogger(ExampleExceptionMapper.class);
	
	@Override
	public Response toResponse(Exception e) {
		//TODO 雑だけどだいたいこんな感じ
		if(e instanceof WebApplicationException){
			logger.warn("WebApplication例外",e);
			WebApplicationException ex = (WebApplicationException)e;
			return ex.getResponse();
		}
		logger.error("原因不明エラー",e);
		HttpProblem p = new HttpProblem();
		p.setStatus(500);
		p.setTitle(e.getMessage());
		p.setDetail(ExceptionUtils.getStackTrace(e));
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(p).build();
	}
	
	public static class HttpProblem{
		private int status;
		private String title;
		private String detail;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
	}
}
