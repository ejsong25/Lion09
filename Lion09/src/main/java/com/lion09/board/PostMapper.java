package com.lion09.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface PostMapper {
	
	public void insertData(Post dto) throws Exception;

	public int maxPostId() throws Exception;
	
	public int getDataCount(@Param("searchKey") String searchKey, @Param("searchValue") String searchValue) throws Exception;
	
	public List<Post> getLists(@Param("start") Integer start,@Param("end") Integer end, @Param("searchKey") String searchKey, @Param("searchValue") String searchValue) throws Exception;
	
	public void updateHitCount(int postId) throws Exception;

	public Post getReadData(int postId) throws Exception;
	
	public void updateData(Post dto) throws Exception;
	
	public void deleteData(int postId) throws Exception;
	
	public void imgUpdate(Post dto) throws Exception;
	
	public void imgDefault(Post dto) throws Exception;
	

}
