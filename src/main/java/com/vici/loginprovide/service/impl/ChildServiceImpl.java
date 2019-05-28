package com.vici.loginprovide.service.impl;

import com.vici.loginprovide.dao.ChildMapper;
import com.vici.loginprovide.dao.ProgramMapper;
import com.vici.loginprovide.entity.GenericEntity;
import com.vici.loginprovide.entity.SevenMonth;
import com.vici.loginprovide.model.Child;
import com.vici.loginprovide.model.Hospital;
import com.vici.loginprovide.model.Program;
import com.vici.loginprovide.service.ChildService;
import com.vici.loginprovide.util.MD5;
import com.vici.loginprovide.util.logUtil.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ChildServiceImpl
 * @Description
 * @Author vici_yyb
 * @Date 2019/1/21 16:01
 * @Version V2.0
 **/
@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildMapper childMapper;

    @Autowired
    private ProgramMapper programMapper;

//    @Autowired
//    private TrainStatusMapper trainStatusMapper;

    /**
     * 儿童注册，添加训练项目状态
     *
     * @param child
     * @return
     */
    @Override
    public String addChild(Child child) {
        Child child1 = new Child();
        if (child.getChildPhone() != null && child.getChildPhone() != "") {
            child1.setChildPhone(child.getChildPhone());
            if (childMapper.selectByPhone(child1) == null) {
                //儿童注册
                child1.setChildName(child.getChildName());
                child1.setChildSex(child.getChildSex());
                child1.setChildAge(child.getChildAge());
                child1.setChildGuardian(child.getChildGuardian());
                child1.setChildCreateTime(new Date());
                //用户在小程序上注册时，不传用户医院id，默认赋值为0
                if (child.getChildHospitalId() != null) {
                    child1.setChildHospitalId(child.getChildHospitalId());
                } else {
                    child1.setChildHospitalId(0);
                }
                child1.setChildModifyTime(new Date());
                child1.setChildPwd(MD5.md5Encrpt(child.getChildPwd()));
                child1.setChildCount(child.getChildCount());
                child1.setChildLock(-1);
                child1.setChildGold(0);
                child1.setChildTrain(0);
                childMapper.addChild(child1);

                //添加训练状态
//                Child child2 = childMapper.selectByPhone(child);
//                TrainStatus trainStatus = new TrainStatus();
//                trainStatus.setTrainStatusChildId(child2.getChildId());
//                trainStatus.setTrainStatusNum(0);
//                trainStatus.setTrainStatusTime(new Date());
//                trainStatusMapper.insert(trainStatus);
                return "0";
            } else {
                return "-1";
            }
        } else {
            return "-2";
        }
    }

    @Override
    public List<Child> selectByName(Child child) {
        List<Child> children = childMapper.selectByName(child);
        return children;
    }

    @Override
    public Child selectByPhone(Child child) {
        Child child1 = childMapper.selectByPhoneHospitalId(child);
        return child1;
    }

//    @Override
//    public String changeChildPwd(Child child) {
//        Child child1 = new Child();
//        if (child.getChildId() == null) {
//            return "-1";
//        } else if (child.getChildHospitalId() == null) {
//            return "-2";
//        } else {
//            child1.setChildId(child.getChildId());
//            child1.setChildPwd(MD5.md5Encrpt(child.getChildPwd()));
//            child1.setChildModifyTime(new Date());
//            childMapper.changeChild(child1);
//            return "0";
//        }
//    }

    @Override
    public String changeChildPwd(Child child, String oldPwd) {
        Child child1 = new Child();
        Child child2 = childMapper.selectByPrimaryKey(child.getChildId());

        //用户不存在
        if (child.getChildId() == null) {
            return "-1";
        }
        //医院id验证失败
        else if (child.getChildHospitalId() == null) {
            return "-2";
        }
        //旧密码验证失败
        else if (!child2.getChildPwd().equals(MD5.md5Encrpt(oldPwd))) {
            return "-3";
        }
        //修改成功
        else {
            child1.setChildId(child.getChildId());
            child1.setChildPwd(MD5.md5Encrpt(child.getChildPwd()));
            child1.setChildModifyTime(new Date());
            childMapper.changeChild(child1);
            return "0";
        }
    }

    @Override
    public String changeChildPhone(Child child, String newPhone) {
        Child child1 = new Child();
        Child child2 = childMapper.selectByPhone(child);

        //用户不存在
        if (child2 == null) {
            return "-1";
        } else if (child.getChildHospitalId() == null) {
            return "-2";
        } else {
            child1.setChildId(child2.getChildId());
            child1.setChildPhone(newPhone);
            child1.setChildModifyTime(new Date());
            childMapper.changeChild(child1);
            return "0";
        }
    }

    /**
     * 完成训练之后向儿童表中添加金币数和更新最近训练时间
     *
     * @param child
     */
    @Override
    public void addGold(Child child) {
        Child child2 = new Child();
        Child child1 = childMapper.selectByPrimaryKey(child.getChildId());
        int i = child1.getChildGold() + child.getChildGold();
        child2.setChildGold(i);
        child2.setChildTrainTime(child.getChildTrainTime());
        child2.setChildId(child.getChildId());
        child2.setChildTrain(child.getChildTrain());
        childMapper.changeChild(child2);
    }

    /**
     * 如果出现未完成训练情况，更新训练次数
     *
     * @param child
     * @return
     */
    @Override
    public int updateChildTrain(Child child) {
        int i = childMapper.updateByPrimaryKeySelective(child);
        return i;
    }


    @Override
    public Child selectById(Child child) {
        Child child1 = childMapper.selectByPrimaryKey(child.getChildId());
        return child1;
    }

    @Override
    public List<Child> selectAllChild(Child child) {

        List<Child> children = childMapper.selectAllChild(child);

        return children;
    }

    @Override
    public List<Child> selectChildInMonth(Child child) {
        List<Child> children = childMapper.selectChildInMonth(child);

        return children;
    }

    @Override
    public String childLogin(Child child, Hospital hospital) {
        Child child1 = childMapper.childLogin(child);
        if (hospital.getHospitalId()==null){
            return "-3";
        }else if (child1 == null) {
            return "-2";
        } else if (!child1.getChildPwd().equals(MD5.md5Encrpt(child.getChildPwd()))) {
            return "-1";
        }else {
            //在小程序上注册的用户，首次登陆的时候绑定医院id
            if (child1.getChildHospitalId()==0){
                child1.setChildHospitalId(hospital.getHospitalId());
                child1.setChildModifyTime(new Date());
                childMapper.updateByPrimaryKeySelective(child1);
                return "1";
            }else {
                return "0";
            }
        }
    }

    @Override
    public GenericEntity childLoginWeChat(Child child) {
        Child child1 = childMapper.childLogin(child);

        GenericEntity genericEntity = new GenericEntity();
        if (child1 == null) {
            genericEntity.setReturnCode("-2");
            return genericEntity;
        } else if (!child1.getChildPwd().equals(MD5.md5Encrpt(child.getChildPwd()))) {
            genericEntity.setReturnCode("-1");
            return genericEntity;
        } else {
            genericEntity.setReturnCode("0");
            genericEntity.setReturnContent(child1);
            return genericEntity;
        }
    }

    /**
     * 用户体验解锁
     *
     * @param child
     * @return
     */
    @Override
    public int updateChildUnlock(Child child) {
        Child child1 = new Child();

        child1.setChildId(child.getChildId());
        child1.setChildLock(0);
        child1.setChildModifyTime(new Date());

        int i = childMapper.updateByPrimaryKeySelective(child1);

        return i;
    }

    /**
     * 查询不活跃用户
     *
     * @param child
     * @return
     */
    @Override
    public List<Child> slumberChild(Child child) {
        Child child1 = new Child();
        child1.setChildHospitalId(child.getChildHospitalId());
        child1.setChildTrainTime(new Date());
        List<Child> children = childMapper.slumberChild(child1);

        /**
         * 查询不活跃用户，排除训练完成的用户
         */
        List<Child> children1 = new ArrayList<>();
        for (Child c : children) {
            Date date = new Date();
            if (c.getChildCutTime() != null && c.getChildCutTime().getTime() > date.getTime()) {
                children1.add(c);
            }
        }

        return children1;
    }

    /**
     * 查询在训用户
     *
     * @param child
     * @return
     */
    @Override
    public List<Child> selectTrainingChild(Child child) {
        Child child1 = new Child();
        child1.setChildHospitalId(child.getChildHospitalId());
        child1.setChildCutTime(new Date());
        List<Child> children = childMapper.selectTrainingChild(child1);
        return children;
    }

    @Override
    public Integer addChildTrain(Child child) throws ParseException {
        Child child1 = new Child();
        Program program = programMapper.selectByPrimaryKey(child.getChildProgram());

        double i = program.getProgramNumber();
        //计算训练周数 使用java获取数值向上取整
        double ceil = Math.ceil(i / 3);
        int a = (int) ceil;
        int num = a * 7 + 30;
        //计算出训练截止时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        String s = TimeUtils.subDay(format, num);
        Date parse = sdf.parse(s);

        child1.setChildCutTime(parse);
        child1.setChildModifyTime(new Date());
        child1.setChildId(child.getChildId());
        child1.setChildCount(program.getProgramNumber());
        child1.setChildProgram(program.getProgramId());

        if (childMapper.selectByPrimaryKey(child1.getChildId()) != null) {
            int i1 = childMapper.updateByPrimaryKeySelective(child1);
            return i1;
        } else {
            return -1;
        }
    }

    /**
     * 根据儿童id查询训练成绩和量表信息
     *
     * @param child
     * @return
     */
    @Override
    public List<Child> selectTrainAndScale(Child child) {

        List<Child> children = childMapper.selectTrainAndScale(child);

        return children;
    }

    /**
     * 用户忘记密码
     *
     * @param child
     * @return
     */
    @Override
    public String forgetChildPwd(Child child) {
        Child child2 = new Child();
        Child child1 = childMapper.selectByPhone(child);
        if (child1 == null) {
            return "-1";
        } else if (child1 != null) {
            child2.setChildId(child1.getChildId());
            child2.setChildPwd(MD5.md5Encrpt(child.getChildPwd()));
            child2.setChildModifyTime(new Date());

            childMapper.updateByPrimaryKeySelective(child2);
            return "0";
        }
        return null;
    }

    @Override
    public HashMap selectSevenMonth(Hospital hospital, Integer num) {
        LinkedHashMap<Integer, Integer> stringIntegerHashMap = new LinkedHashMap<>();
        for (int i = num - 1; i >= 0; i--) {
            Integer i1 = childMapper.selectAddOneMonth(hospital.getHospitalId(), i);
            stringIntegerHashMap.put(i, i1);
        }
        return stringIntegerHashMap;
    }

    @Override
    public SevenMonth selectSevenMonth1(Hospital hospital) {
        SevenMonth sevenMonth = new SevenMonth();
        LinkedList<Object> objects = new LinkedList<>();
        int num = 12;
        for (int i = 0; i < num; i++) {
            Integer i1 = childMapper.selectAddOneMonth(hospital.getHospitalId(), i);
            objects.add(i1);
        }
        sevenMonth.setOne((Integer) objects.get(11));
        sevenMonth.setTwo((Integer) objects.get(10));
        sevenMonth.setThree((Integer) objects.get(9));
        sevenMonth.setFour((Integer) objects.get(8));
        sevenMonth.setFive((Integer) objects.get(7));
        sevenMonth.setSix((Integer) objects.get(6));
        sevenMonth.setSeven((Integer) objects.get(5));
        sevenMonth.setEight((Integer) objects.get(4));
        sevenMonth.setNine((Integer) objects.get(3));
        sevenMonth.setTen((Integer) objects.get(2));
        sevenMonth.setEleven((Integer) objects.get(1));
        sevenMonth.setTwelve((Integer) objects.get(0));

        return sevenMonth;
    }


    @Override
    public int selectTrainedOnWeek(Child child) {

        int i = childMapper.selectTrainedOnWeek(child);

        return i;
    }


}
