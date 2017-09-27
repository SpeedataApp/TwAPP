package com.example.twapp.utils;

import com.example.twapp.control.TwApplication;
import com.example.twapp.db.TwBody;
import com.example.twapp.db.TwBodyDao;

import java.util.List;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author :孙天伟 in  2017/9/27   13:32.
 *         联系方式:QQ:420401567
 *         功能描述:  数据库增删改查
 */
public class DBUitl {
    public DBUitl() {
    }

    TwBodyDao mDao = TwApplication.getsInstance().getDaoSession().getTwBodyDao();

    /**
     * 添加一条数据
     *
     * @param body
     */
    public void insertDtata(TwBody body) {
        mDao.insertOrReplace(body);
    }

    /**
     * 查找数据
     *
     * @param pNum  腕带编号
     * @param runNum 体温标签编号
     * @return
     */
    public TwBody whereData(String pNum, String runNum) {
        TwBody user = mDao.queryBuilder().where(TwBodyDao.Properties.PeopleNun.eq(pNum),
                TwBodyDao.Properties.RunningNumber.eq(runNum)).build().unique();
        return user;
    }

    /**
     * 查数据
     * @param runNum 体温标签编号
     * @return
     */
    public boolean whereRunNum(String runNum) {
        TwBody user = mDao.queryBuilder().where(TwBodyDao.Properties.RunningNumber.eq(runNum)).build().unique();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  根据 体温标签编号 查找整条数据
     * @param runNum
     * @return
     */
    public TwBody queryTwBody(String runNum) {
        TwBody user = mDao.queryBuilder().where(TwBodyDao.Properties.RunningNumber.eq(runNum)).build().unique();
        return user;
    }

    /**
     * 查找所有数据
     * @return
     */
    public List<TwBody> queryAll() {
        List<TwBody> twBodies = mDao.loadAll();
        if (twBodies != null && twBodies.size() > 0)
            return twBodies;
        return twBodies;
    }

    /**
     * 根据体温标签编号修改数据
     *
     * @param listTime
     * @param listTemperatures
     */
    public void cahageData(List<String> listTime, List<String> listTemperatures, String runNum) {

        TwBody user = mDao.queryBuilder().where(
                TwBodyDao.Properties.RunningNumber.eq(runNum)).build().unique();
        if (user != null) {
            user.setTwTime(listTime);
            user.setTemperatures(listTemperatures);
            mDao.update(user);
        }
    }

    /**
     * 修改指定数据
     *
     * @param runNum
     * @param time
     */
    public void cahageData(String runNum, long time) {

        TwBody user = mDao.queryBuilder().where(
                TwBodyDao.Properties.RunningNumber.eq(runNum)).build().unique();
        if (user != null) {
            user.setFirstTime(time);
            mDao.update(user);
        }
    }

}
