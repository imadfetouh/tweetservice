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
        Long lastWeekDate = DateTime.getInstance().getCurrentDateMinusWeek();

        Query query = session.createQuery("SELECT new com.imadelfetouh.tweetservice.model.dto.TrendDTO(t.name) FROM Trend t WHERE t.name IN (SELECT tt.trend.name FROM TweetTrend tt WHERE tt.tweet.date BETWEEN :lastWeek AND :currentDate ORDER BY tt.tweet.date DESC)");
        query.setParameter("lastWeek", lastWeekDate);
        query.setParameter("currentDate", currentDate);

        List<TrendDTO> trendDTOS = query.getResultList();

        responseModel.setResponseType((trendDTOS.isEmpty()) ? ResponseType.EMPTY : ResponseType.CORRECT);
        responseModel.setData(trendDTOS);

        return responseModel;
    }
}
