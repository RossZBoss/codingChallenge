package com.test.codingChallenge.dao;

import com.test.codingChallenge.entities.ApplicationLogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDao extends CrudRepository<ApplicationLogEntry, Long>
{
    ApplicationLogEntry findByLogId(String logId);

    List<ApplicationLogEntry> findAllByAlertIsTrue();
}
