package vn.edu.likelion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.repository.TrackCourseRepository;
import vn.edu.likelion.service.TrackCourseInterface;

@Service
public class TrackCourseServiceImpl implements TrackCourseInterface {
    @Autowired private TrackCourseRepository trackCourseRepository;


}
