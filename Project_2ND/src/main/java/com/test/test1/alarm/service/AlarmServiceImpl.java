package com.test.test1.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.test1.alarm.dao.AlarmDao;

/* 02.24 장재호 */
@Service
public class AlarmServiceImpl implements AlarmService{

	@Autowired
	AlarmDao alarmDao;
	
	@Override
	public void alarmDel(int idx) {
		alarmDao.alarmDel(idx);
	}

}
/*******************************************/