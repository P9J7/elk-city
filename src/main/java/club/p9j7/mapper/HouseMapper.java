package club.p9j7.mapper;

import club.p9j7.model.House;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface HouseMapper {
    @Insert("insert into house(code,url,title,subtitle,favcount,price,unitprice,roomMainInfo,roomSubInfo,roomMainType,roomSubType,areaMainInfo,areaSubInfo,communityName,areaName) value (#{code},#{url},#{title},#{subtitle},#{favcount},#{price},#{unitprice},#{roomMainInfo},#{roomSubInfo},#{roomMainType},#{roomSubType},#{areaMainInfo},#{areaSubInfo},#{communityName},#{areaName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertHouse(House house);
}
