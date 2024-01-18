package com.kh.cinepic.controller;

import com.kh.cinepic.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    // movie

    // movie_post

    // movie_comment

}
