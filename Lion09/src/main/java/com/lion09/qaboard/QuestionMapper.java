package com.lion09.qaboard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {
	
	public int maxNum() throws Exception;
	
	public void insertData(@Param("dto")QuestionDTO dto, @Param("userId")String userId) throws Exception;
	
	public int getDataCount() throws Exception;
	
	public List<QuestionDTO> getLists(@Param("start")Integer start,@Param("end")Integer end) throws Exception;
	
	public QuestionDTO getReadData(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public void updateData(QuestionDTO dto) throws Exception;
	
	public void deleteData(int num) throws Exception;
	
	
	
	
	

}
