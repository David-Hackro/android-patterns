package com.example.pattern.strategyCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pattern.strategyCache.strategy.StrategyContext;
import com.example.pattern.strategyCache.strategy.inMemoryCache;

import android.content.Context;

public class CommentCache implements IComment {
	
	private CommentsDataAccessObject commentDAO;
	private StrategyContext strategyContext;
	
	public CommentCache(CommentsDataAccessObject commentDAO){
		this.commentDAO = commentDAO;
		strategyContext = new StrategyContext(new inMemoryCache());
	}
	
	@Override
	public Comment getComment(long commentId) {
		//check if exists in cache (in-memory in this example) 
		Comment commentToRetrieve = strategyContext.getComment(commentId);
		
		//retrieve from data source if it is not in cache
		if(commentToRetrieve ==null){
			Comment comment = commentDAO.getComment(commentId);
			strategyContext.storeComment(comment);
			
			return comment;
		}
		return commentToRetrieve;
	}


}
