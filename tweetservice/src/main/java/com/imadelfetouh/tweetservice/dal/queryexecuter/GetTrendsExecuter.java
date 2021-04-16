package com.imadelfetouh.tweetservice.dal.queryexecuter;

import com.imadelfetouh.tweetservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.tweetservice.model.datetime.DateTime;
import com.imadelfetouh.tweetservice.model.dto.TrendDTO;
import com.imadelfetouh.tweetservice.model.response.ResponseModel;
import com.imadelfetouh.tweetservice.model.response.ResponseType;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.List;

public class GetTrendsExecuter implements QueryExecuter<List<TrendDTO>> {

    @Override
    public ResponseModel<List<TrendDTO>> executeQuery(Session session) {
        ResponseModel<List<TrendDTO>> responseModel = new ResponseModel<>();

        Long currentDate = DateTime.getInstance().getCurrentDate();

        Query query = session.createQuery("SELECT new com.imadelfetouh.tweetservice.model.dto.TrendDTO(t.name) FROM Trend t WHERE t.name IN (SELECT tt.trend.name FROM TweetTrend tt WHERE tt.tweet.date <= :date ORDER BY tt.tweet.likes DESC)");
        query.setParameter("date", currentDate);

        List<TrendDTO> trendDTOS = query.getResultList();

        responseModel.setResponseType((trendDTOS.isEmpty()) ? ResponseType.EMPTY : ResponseType.CORRECT);
        responseModel.setData(trendDTOS);

        return responseModel;
    }
}
