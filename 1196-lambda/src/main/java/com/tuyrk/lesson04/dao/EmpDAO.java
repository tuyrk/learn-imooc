package com.tuyrk.lesson04.dao;

import com.tuyrk.lesson04.entity.Department;
import com.tuyrk.lesson04.entity.Employee;
import com.tuyrk.lesson04.mode.IStrategy;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tuyrk
 */
@Repository
public class EmpDAO implements BaseDAO<Employee> {

    private static List<Employee> LIST = new ArrayList<Employee>() {
        {
            add(new Employee(1L, "宋江", "天魁星·及时雨", "头领", null, strToDate("2015-11-08"), 800, 200000, new Department(1L)));
            add(new Employee(2L, "卢俊义", "天罡星·玉麒麟", "卢俊义", 1L, strToDate("2018-04-06"), 800, 100000, new Department(1L)));
            add(new Employee(3L, "吴用", "天机星·智多星", "头领", 1L, strToDate("2012-08-10"), 800, 100000, new Department(1L)));
            add(new Employee(21L, "关胜", "天勇星·大刀", "五虎上将", 1L, strToDate("2017-04-06"), 20000, 2000, new Department(20L)));
            add(new Employee(22L, "林冲", "天雄星 豹子头", "五虎上将", 1L, strToDate("2018-04-06"), 21000, 1800, new Department(20L)));
            add(new Employee(23L, "秦明", "天猛星 霹雳火", "五虎上将", 1L, strToDate("2018-04-06"), 19000, 1900, new Department(20L)));
            add(new Employee(24L, "呼延灼", "天威星 双鞭", "五虎上将", 1L, strToDate("2019-04-06"), 22000, 1100, new Department(20L)));
            add(new Employee(25L, "董平", "天立星·双枪将", "五虎上将", 1L, strToDate("2017-04-06"), 18000, 1200, new Department(20L)));
            add(new Employee(2101L, "花荣", "天英星 小李广", "骑兵头领", 1L, strToDate("2016-04-06"), 16000, 2000, new Department(21L)));
            add(new Employee(2102L, "徐宁", "天佑星 金枪手", "骑兵都统", 2101L, strToDate("2015-04-06"), 12000, 1200, new Department(21L)));
            add(new Employee(2103L, "杨志", "天暗星 青面兽", "骑兵都统", 2101L, strToDate("2016-04-06"), 13000, 1200, new Department(21L)));
            add(new Employee(2104L, "索超", "天空星 急先锋", "骑兵都统", 2101L, strToDate("2018-04-06"), 16000, 1100, new Department(21L)));
            add(new Employee(2105L, "张青", "地刑星 菜园子", "骑兵都统", 2101L, strToDate("2016-04-06"), 14000, 1000, new Department(21L)));
            add(new Employee(2106L, "史进", "天微星 九纹龙", "骑兵都统", 2101L, strToDate("2017-04-06"), 15000, 1100, new Department(21L)));
            add(new Employee(2107L, "穆虹", "天究星 没遮拦", "骑兵都统", 2101L, strToDate("2015-04-06"), 14000, 1000, new Department(21L)));
            add(new Employee(2108L, "朱仝", "天满星 美髯公", "骑兵都统", 2101L, strToDate("2016-04-06"), 14000, 1000, new Department(21L)));
            add(new Employee(2109L, "王英", "地微星 矮脚虎", "骑兵都统", 2101L, strToDate("2017-04-06"), 12000, 900, new Department(21L)));
            add(new Employee(2110L, "扈三娘", "地慧星 一丈青", "骑兵都统", 2101L, strToDate("2017-04-06"), 11000, 800, new Department(21L)));
            add(new Employee(2111L, "吕方", "地佐星 小温侯", "骑兵都统", 2101L, strToDate("2016-04-06"), 12000, 900, new Department(21L)));
            add(new Employee(2112L, "郭盛", "地佑星 赛仁贵", "骑兵都统", 2101L, strToDate("2016-04-06"), 12000, 900, new Department(21L)));
            add(new Employee(2201L, "鲁智深", "天孤星 花和尚", "步军头领", 1L, strToDate("2015-04-06"), 16000, 2000, new Department(22L)));
            add(new Employee(2202L, "武松", "天伤星 行者", "步军都统", 2201L, strToDate("2015-04-06"), 16000, 1400, new Department(22L)));
            add(new Employee(2203L, "刘唐", "天异星 赤髪鬼", "步军都统", 2201L, strToDate("2014-04-06"), 13000, 1200, new Department(22L)));
            add(new Employee(2204L, "雷横", "天退星 插翅虎", "步军都统", 2201L, strToDate("2016-04-06"), 12000, 1200, new Department(22L)));
            add(new Employee(2205L, "李逵", "天杀星 黑旋风", "步军都统", 2201L, strToDate("2018-04-06"), 11000, 1200, new Department(22L)));
            add(new Employee(2206L, "燕青", "天巧星 浪子", "步军都统", 2201L, strToDate("2016-04-06"), 13000, 1200, new Department(22L)));
            add(new Employee(2207L, "石秀", "天慧星·拚命三郎", "步军都统", 2201L, strToDate("2015-04-06"), 12000, 1200, new Department(22L)));
            add(new Employee(2208L, "杨雄", "天牢星 病关索", "步军都统", 2201L, strToDate("2014-04-06"), 13000, 1200, new Department(22L)));
            add(new Employee(2209L, "解珍", "天暴星 两头蛇", "步军都统", 2201L, strToDate("2013-04-06"), 12000, 1200, new Department(22L)));
            add(new Employee(2210L, "解宝", "天哭星 双尾蝎", "步军都统", 2201L, strToDate("2013-04-06"), 12000, 1200, new Department(22L)));
            add(new Employee(2211L, "孔明", "地猖星 毛头星", "步军偏将", 2201L, strToDate("2015-04-06"), 13000, 1100, new Department(22L)));
            add(new Employee(2212L, "孔亮", "地狂星 独火星", "步军偏将", 2201L, strToDate("2016-04-06"), 13000, 1000, new Department(22L)));
            add(new Employee(2213L, "樊瑞", "地默星 混世魔王", "步军偏将", 1201L, strToDate("2014-04-06"), 12000, 1000, new Department(22L)));
            add(new Employee(2214L, "鲍旭", "地暴星 丧门神", "步军偏将", 1201L, strToDate("2015-04-06"), 12000, 900, new Department(22L)));
            add(new Employee(2215L, "项充", "地飞星 八臂哪吒", "步军偏将", 2201L, strToDate("2016-04-06"), 11000, 900, new Department(22L)));
            add(new Employee(2216L, "李衮", "地走星 飞天大圣", "步军偏将", 2201L, strToDate("2016-04-06"), 12000, 1100, new Department(22L)));
            add(new Employee(2217L, "薛永", "地幽星 病大虫", "步军偏将", 2201L, strToDate("2014-04-06"), 11000, 900, new Department(22L)));
            add(new Employee(2218L, "施恩", "地伏星 金眼彪", "步军偏将", 2201L, strToDate("2014-04-06"), 12000, 900, new Department(22L)));
            add(new Employee(2219L, "穆春", "地镇星 小遮拦", "步军偏将", 2201L, strToDate("2015-04-06"), 12800, 900, new Department(22L)));
            add(new Employee(2220L, "李忠", "地僻星 打虎将", "步军偏将", 2201L, strToDate("2013-04-06"), 11000, 1100, new Department(22L)));
            add(new Employee(2221L, "郑天寿", "地异星·白面郎君", "步军偏将", 2201L, strToDate("2016-04-06"), 12000, 1100, new Department(22L)));
            add(new Employee(2222L, "宋万", "地魔星 云里金刚", "步军偏将", 2201L, strToDate("2016-04-06"), 11000, 900, new Department(22L)));
            add(new Employee(2223L, "杜迁", "地妖星 摸着天", "步军偏将", 2201L, strToDate("2017-04-06"), 10000, 900, new Department(22L)));
            add(new Employee(2224L, "邹渊", "地短星 出林龙", "步军偏将", 2201L, strToDate("2017-04-06"), 10000, 900, new Department(22L)));
            add(new Employee(2225L, "邹润", "地角星 独角龙", "步军偏将", 2201L, strToDate("2018-04-06"), 10000, 1100, new Department(22L)));
            add(new Employee(2226L, "龚旺", "地捷星 花项虎", "步军偏将", 2201L, strToDate("2018-04-06"), 11000, 1000, new Department(22L)));
            add(new Employee(2227L, "丁得孙", "地速星 中箭虎", "步军偏将", 2201L, strToDate("2018-04-06"), 12000, 900, new Department(22L)));
            add(new Employee(2228L, "焦挺", "地恶星 没面目", "步军偏将", 2201L, strToDate("2018-04-06"), 11000, 900, new Department(22L)));
            add(new Employee(2229L, "石勇", "地丑星 石将军", "步军偏将", 2201L, strToDate("2018-04-06"), 13000, 900, new Department(22L)));
            add(new Employee(2301L, "李俊", "天寿星 混江龙", "水军头领", 1L, strToDate("2016-04-06"), 16000, 2000, new Department(23L)));
            add(new Employee(2302L, "张横", "天平星 船火儿", "水军都统", 2301L, strToDate("2017-04-06"), 13000, 1000, new Department(23L)));
            add(new Employee(2303L, "张顺", "天损星 浪里白条", "水军都统", 2301L, strToDate("2018-04-06"), 12000, 1100, new Department(23L)));
            add(new Employee(2304L, "阮小二", "天剑星 立地太岁", "水军都统", 2301L, strToDate("2014-04-06"), 14000, 1000, new Department(23L)));
            add(new Employee(2305L, "阮小五", "天罪星 短命二郎", "水军都统", 2301L, strToDate("2014-04-06"), 14000, 1200, new Department(23L)));
            add(new Employee(2306L, "阮小七", "天败星 活阎罗", "水军都统", 2301L, strToDate("2014-04-06"), 14000, 1000, new Department(23L)));
            add(new Employee(2307L, "童威", "地进星 出洞蛟", "水军偏将", 2301L, strToDate("2018-04-06"), 13000, 1100, new Department(23L)));
            add(new Employee(2308L, "童猛", "地退星 翻江蜃", "水军偏将", 2301L, strToDate("2018-04-06"), 12000, 1000, new Department(23L)));
            add(new Employee(3001L, "柴进", "天贵星 小旋风", "财务部长", 1L, strToDate("2014-04-06"), 15000, 2000, new Department(30L)));
            add(new Employee(3002L, "李应", "天富星 扑天雕", "财务会计", 3001L, strToDate("2014-04-06"), 13000, 2000, new Department(30L)));
            add(new Employee(3003L, "皇甫端", "地兽星 紫髯伯", "财务会计", 3001L, strToDate("2015-04-06"), 13000, 2000, new Department(30L)));
            add(new Employee(4001L, "公孙胜", "天闲星 入云龙", "参谋长", 1L, strToDate("2015-08-06"), 15000, 2000, new Department(40L)));
            add(new Employee(4002L, "张清", "天捷星 没羽箭", "参谋", 4001L, strToDate("2016-09-15"), 13000, 2000, new Department(40L)));
            add(new Employee(4003L, "朱武", "地魁星 神机军师", "参谋", 4001L, strToDate("2017-06-20"), 13000, 2000, new Department(40L)));
            add(new Employee(4004L, "安道全", "地灵星 神医", "参谋", 4001L, strToDate("2015-10-18"), 13000, 2000, new Department(40L)));
            add(new Employee(4005L, "宋清", "地俊星 铁扇子", "参谋", 4001L, strToDate("2018-11-16"), 13000, 2000, new Department(40L)));
            add(new Employee(5001L, "金大坚", "地巧星 玉臂匠", "后勤部长", 1L, strToDate("2015-02-01"), 9000, 3000, new Department(50L)));
            add(new Employee(5002L, "蒋敬", "地会星 神算子", "后勤杂事", 5001L, strToDate("2015-04-20"), 9000, 2000, new Department(50L)));
            add(new Employee(5003L, "孟康", "地满星 玉幡竿", "后勤杂事", 5001L, strToDate("2016-05-10"), 9000, 2000, new Department(50L)));
            add(new Employee(5004L, "侯键", "地遂星 通臂猿", "后勤杂事", 5001L, strToDate("2016-08-16"), 9000, 2000, new Department(50L)));
            add(new Employee(5005L, "裴宣", "地正星 铁面孔目", "后勤杂事", 5001L, strToDate("2017-12-03"), 9000, 2000, new Department(50L)));
            add(new Employee(5006L, "汤隆", "地孤星 金钱豹子", "后勤杂事", 5001L, strToDate("2017-01-20"), 9000, 2000, new Department(50L)));
            add(new Employee(5007L, "凌阵", "地辅星 轰天雷", "后勤杂事", 5001L, strToDate("2018-08-20"), 9000, 2000, new Department(50L)));
            add(new Employee(5008L, "李云", "地察星 青眼虎", "后勤杂事", 5001L, strToDate("2018-08-21"), 9000, 2000, new Department(50L)));
            add(new Employee(5009L, "曹正", "地羁星 操刀鬼", "后勤杂事", 5001L, strToDate("2018-09-10"), 9000, 2000, new Department(50L)));
            add(new Employee(5010L, "朱富", "地藏星 笑面虎", "后勤杂事", 5001L, strToDate("2018-09-15"), 9000, 2000, new Department(50L)));
            add(new Employee(5011L, "陶宗旺", "地理星 九尾龟", "后勤杂事", 5001L, strToDate("2018-09-22"), 9000, 2000, new Department(50L)));
            add(new Employee(5012L, "郁保四", "地健星 险道神", "后勤杂事", 5001L, strToDate("2018-10-06"), 9000, 2000, new Department(50L)));
            add(new Employee(6001L, "戴宗", "天速星 神行太保", "军情部长", 1L, strToDate("2014-02-16"), 5000, 10000, new Department(50L)));
            add(new Employee(6002L, "乐和", "地乐星 铁叫子", "军情都统", 6001L, strToDate("2015-12-13"), 2000, 8000, new Department(50L)));
            add(new Employee(6003L, "时迁", "地贼星 鼓上蚤", "军情都统", 6001L, strToDate("2015-10-16"), 2000, 8000, new Department(50L)));
            add(new Employee(6004L, "段景住", "地狗星 金毛犬", "军情都统", 6001L, strToDate("2016-06-19"), 2000, 8000, new Department(50L)));
            add(new Employee(6005L, "白胜", "地耗星 白日鼠", "军情都统", 6001L, strToDate("2016-08-20"), 2000, 8000, new Department(50L)));
            add(new Employee(6006L, "黄信", "地煞星 镇三山", "军情远哨", 6001L, strToDate("2017-04-06"), 2000, 8000, new Department(50L)));
            add(new Employee(6007L, "孙立", "地勇星 病尉迟", "军情远哨", 6001L, strToDate("2018-12-06"), 2000, 8000, new Department(50L)));
            add(new Employee(6008L, "宣赞", "地杰星 丑郡马", "军情远哨", 6001L, strToDate("2018-09-30"), 2000, 8000, new Department(50L)));
            add(new Employee(6009L, "郝思文", "地雄星 井木犴", "军情远哨", 6001L, strToDate("2018-05-21"), 2000, 8000, new Department(50L)));
            add(new Employee(6010L, "韩滔", "地威星 百胜将", "军情远哨", 6001L, strToDate("2018-05-21"), 2000, 8000, new Department(50L)));
            add(new Employee(6011L, "彭屺", "地英星 天目将", "军情远哨", 6001L, strToDate("2017-02-16"), 2000, 8000, new Department(50L)));
            add(new Employee(6012L, "单廷圭", "地奇星 圣水将", "军情远哨", 6001L, strToDate("2016-10-06"), 2000, 8000, new Department(50L)));
            add(new Employee(6013L, "魏定国", "地猛星 神火将", "军情远哨", 6001L, strToDate("2018-12-03"), 2000, 8000, new Department(50L)));
            add(new Employee(6014L, "欧鹏", "地辟星 摩云金翅", "军情远哨", 6001L, strToDate("2017-11-04"), 2000, 8000, new Department(50L)));
            add(new Employee(6015L, "邓飞", "地阖星 火眼狻猊", "军情远哨", 6001L, strToDate("2017-11-05"), 2000, 8000, new Department(50L)));
            add(new Employee(6016L, "燕顺", "地强星 锦毛虎", "军情远哨", 6001L, strToDate("2018-10-16"), 2000, 8000, new Department(50L)));
            add(new Employee(6017L, "马麟", "地明星 铁笛仙", "军情远哨", 6001L, strToDate("2018-10-16"), 2000, 8000, new Department(50L)));
            add(new Employee(6018L, "陈达", "地周星 跳涧虎", "军情远哨", 6001L, strToDate("2018-09-20"), 2000, 8000, new Department(50L)));
            add(new Employee(6019L, "杨春", "地隐星 白花蛇", "军情远哨", 6001L, strToDate("2018-09-20"), 2000, 8000, new Department(50L)));
            add(new Employee(6020L, "杨林", "地暗星 锦豹子", "军情远哨", 6001L, strToDate("2018-09-20"), 2000, 8000, new Department(50L)));
            add(new Employee(6021L, "周通", "地空星 小霸王", "军情远哨", 6001L, strToDate("2018-05-30"), 2000, 8000, new Department(50L)));
            add(new Employee(7001L, "孙新", "地数星 小尉迟", "东山迎宾", 3L, strToDate("2014-08-06"), 8000, 9000, new Department(70L)));
            add(new Employee(7002L, "顾大嫂", "地阴星 母大虫", "东山迎宾", 3L, strToDate("2014-08-06"), 8000, 9000, new Department(70L)));
            add(new Employee(7003L, "张青", "地刑星 菜园子", "西山迎宾", 3L, strToDate("2015-06-12"), 8000, 9000, new Department(70L)));
            add(new Employee(7004L, "孙儿娘", "地壮星 母夜叉", "西山迎宾", 3L, strToDate("2015-06-12"), 8000, 9000, new Department(70L)));
            add(new Employee(7005L, "朱贵", "地囚星 旱地忽律", "南山迎宾", 3L, strToDate("2013-05-25"), 8000, 9000, new Department(70L)));
            add(new Employee(7006L, "杜兴", "地全星 鬼睑儿", "南山迎宾", 3L, strToDate("2013-05-26"), 8000, 9000, new Department(70L)));
            add(new Employee(7007L, "李立", "地奴星 催命判官", "北山迎宾", 3L, strToDate("2015-04-18"), 8000, 9000, new Department(70L)));
            add(new Employee(7008L, "王定六", "地劣星 活闪婆", "北山迎宾", 3L, strToDate("2015-04-18"), 8000, 9000, new Department(70L)));
            add(new Employee(8001L, "蔡福", "地平星 铁臂膊", "刑罚堂主", 3L, strToDate("2013-07-10"), 12000, 2000, new Department(80L)));
            add(new Employee(8002L, "蔡庆", "地损星 一枝花", "刑罚执法", 3L, strToDate("2013-07-10"), 12000, 2000, new Department(80L)));
        }

        private Date strToDate(String str) {
            return Date.from(LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    };

    @Override
    public boolean add(Employee employee) {
        return LIST.add(employee);
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Employee employee) {
        return false;
    }

    @Override
    public Employee findById(String id) {
        return LIST.stream().filter(x -> x.getEmpNo().equals(Long.parseLong(id))).findAny().orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return LIST;
    }

    @Override
    public List<Employee> findByStrategy(IStrategy strategy) {
        List<Employee> tempList = new ArrayList<>();
        for (Employee employee : LIST) {
            if (strategy.test(employee)) {
                tempList.add(employee);
            }
        }
        return tempList;
    }
}
