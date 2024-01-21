package com.kh.cinepic.service;

import com.kh.cinepic.entity.Bookmark;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.repository.BookmarkRepository;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final MovieService movieService;

    // 북마크 여부 확인
    public boolean isBookMarked(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("해당 영화가 존재하지 않습니다."));

        boolean isBookMark = bookmarkRepository.existsByMemberAndMovie(member, movie);
        log.info("{} bookMarked {}", movie.getMovieTitle(), isBookMark);
        return isBookMark;
    }

    // 북마크 저장
    public boolean saveBookMark(Long memberId, Long movieId) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("해당 영화가 존재하지 않습니다."));

            Bookmark bookmark = new Bookmark();
            bookmark.setMember(member);
            bookmark.setMovie(movie);
            bookmarkRepository.save(bookmark);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 북마크 해제
    public boolean removeBookMark(Long memberId, Long movieId) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재 하지 않습니다."));
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("영화가 존재 하지 않습니다."));

            Bookmark bookmark = bookmarkRepository.findByMemberAndMovie(member, movie)
                    .orElseThrow(() -> new RuntimeException("해당 북마크 정보가 없습니다."));

            bookmarkRepository.delete(bookmark);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 회원 북마크 정보 불러오기



}
