package club.p9j7.mapper;

import club.p9j7.model.Aqi;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AqiMapper {
    @Insert({
            "<script>",
            "insert into aqi(aqi, city, quality, pm2_5, pm10, so2, co, no2, o3, timePoint) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.aqi}, #{item.city}, #{item.quality},#{item.pm2_5}, #{item.pm10}, #{item.so2}, #{item.co}, #{item.no2}, #{item.o3}, #{item.timePoint})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertAqiList(List<Aqi> aqiList);

    @Select("select count(*) from aqi where city = #{city}")
    int countByCity(String city);
}
