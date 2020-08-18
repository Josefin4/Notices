package se.experis.academy.sessions.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.experis.academy.sessions.model.Notice;

import java.util.List;


@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {


    List<Notice> findByActiveTrueOrderByDateDesc();


    Notice findById(long id);



    @Override
    <S extends Notice> S save(S s);

}

