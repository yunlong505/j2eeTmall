package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public int getTotal() {
        int total = 0;
        try {
            Connection c = DBUtil.getConnection();
            String sql = "select count(*) from Category";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            s.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }


    public void add(Category bean) {
        try {
            Connection c = DBUtil.getConnection();
            String sql = "insert into Category values(null,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, bean.getName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
            ps.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Category bean) {
        try {
            Connection c = DBUtil.getConnection();
            String sql = "update category set name=? where id =?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, bean.getName());
            ps.setInt(2, bean.getId());
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            Connection c = DBUtil.getConnection();
            String sql = "delete from category where id =" + id;
            Statement s = c.createStatement();
            s.execute(sql);
            s.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category get(int id) {
        Category bean = null;
        try {
            Connection c = DBUtil.getConnection();
            String sql = "select *from category where id =" + id;
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                bean = new Category();
                bean.setId(id);
                bean.setName(rs.getString(2));
            }
            s.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Category> list(int start, int count) {
        List<Category> beans = new ArrayList<>();
        try {
            Connection c = DBUtil.getConnection();
            String sql = "select * from category order by id desc limit ?,?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category bean = new Category();
                bean.setId(rs.getInt(1));
                bean.setName(rs.getString(2));
                beans.add(bean);
            }
            ps.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

}
