package com.lion09.qaboard;

import java.util.List;

public interface QuestionService {
	
	public void insertData(QuestionDTO dto, String userId) throws Exception;
	
	public int maxNum() throws Exception;
		
	public int getDataCount() throws Exception;
	
	public List<QuestionDTO> getLists(int start, int end) throws Exception;
	
	public QuestionDTO getReadData(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public void updateData(QuestionDTO dto) throws Exception;
	
	public void deleteData(int num) throws Exception;
	
	
}