package com.lion09.qaboard;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionMapper questionMapper;
	
	 @Autowired
	 public QuestionServiceImpl(QuestionMapper questionMapper) {
	        this.questionMapper = questionMapper;
	   }
	
	@Override
	public int maxNum() throws Exception {
		
		return questionMapper.maxNum();
	}

	@Override
	public void insertData(QuestionDTO dto) throws Exception {
		
		questionMapper.insertData(dto);
	}

	@Override
	public int getDataCount() throws Exception {
		
		return questionMapper.getDataCount() ;
	}

	@Override
	public List<QuestionDTO> getLists(int start, int end) throws Exception {
		
		return questionMapper.getLists(start, end);
	}

	@Override
	public QuestionDTO getReadData(int num) throws Exception {
		
		return questionMapper.getReadData(num);
	}

	@Override
	public void updateHitCount(int num) throws Exception {
		
		questionMapper.updateHitCount(num);
		
	}

	@Override
	public void updateData(QuestionDTO dto) throws Exception {
		
		questionMapper.updateData(dto);
		
	}

	@Override
	public void deleteData(int num) throws Exception {
		
		questionMapper.deleteData(num);
		
	}
	


}
