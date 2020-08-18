package se.experis.academy.sessions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.sessions.model.Notice;
import se.experis.academy.sessions.model.Response;
import se.experis.academy.sessions.repository.NoticeRepository;

import java.util.List;


@RestController
@RequestMapping("/api")
public class NoticeController {

    @Autowired
    NoticeRepository noticeRepository;


    @GetMapping("/notices")
    public ResponseEntity<Response> getNotices() {
        Response response;
        HttpStatus status;
        List<Notice> notices = noticeRepository.findByActiveTrueOrderByDateDesc();
        if (notices != null && !notices.isEmpty()){
            response = new Response(notices, "SUCCESS");
            status = HttpStatus.OK;
        } else {
            response = new Response(null, "NO NOTICES");
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(response, status);
    }


    @DeleteMapping("/notice/delete/{id}")
    public ResponseEntity<Response> deleteNotice(@PathVariable("id") long id) {
        Response response;
        HttpStatus status;
        if (noticeRepository.existsById(id)) {
            Notice notice = noticeRepository.findById(id);
            notice.setInactive();
            noticeRepository.save(notice);
            response = new Response(null, "DELETED");
            status = HttpStatus.OK;
        } else {
            response = new Response(null, "COULDN'T DELETE");
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, status);
    }


    @PostMapping("/notice/create")
    public ResponseEntity<Response> addNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeRepository.save(notice);
        Response response = new Response(createdNotice, "CREATED");
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(response, status);
    }


    @PatchMapping("/notice/update")
    public ResponseEntity<Response> updateNotice(@RequestBody Notice notice) {
        Response response;
        HttpStatus httpStatus;
        if (noticeRepository.existsById(notice.getId())) {
            Notice modifiedNotice = noticeRepository.save(notice);
            response = new Response(modifiedNotice, "MODIFIED");
            httpStatus = HttpStatus.OK;
        } else {
            response = new Response(null, "NOTICE DOESN'T EXIST");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}
