package com.kh.cinepic.documents;

import com.kh.cinepic.common.MovieInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



@Getter @Setter
@Document(indexName = "movies")
public class MovieDocument implements MovieInfo {

    @Id
    private Long movieId;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer_with_stopwords")
    private String movieTitle;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String movieTitleEng;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd")
    private String movieRelease;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer_with_stopwords")
    private String movieGenre;

    @Field(type = FieldType.Text)
    private String movieNation;

    @Field(type = FieldType.Text)
    private String movieGrade;

    @Field(type = FieldType.Keyword)
    private String movieRuntime;

    @Field(type = FieldType.Keyword)
    private String movieScore;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer_with_stopwords")
    private String movieDirector;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer_with_stopwords")
    private String movieActors;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer_with_stopwords")
    private String moviePlot;

    @Field(type = FieldType.Keyword)
    private String moviePoster;

    @Field(type = FieldType.Keyword)
    private String movieStills;

}
