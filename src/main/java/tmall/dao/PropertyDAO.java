package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {
    public int getTotal(int cid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select count(*) from Property where cid =" + cid;

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Property bean) {

        String sql = "insert into Property values(null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Property bean) {

        String sql = "update Property set cid= ?, name=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.setInt(3, bean.getId());
            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "delete from Property where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Property get(int id) {
        Property bean = new Property();

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from Property where id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {

                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<>();
        try {
            Connection c = DBUtil.getConnection();
            String sql = "select * from property where cid = ? order by id desc LIMIT ? , ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Property bean = new Property();
                bean.setId(rs.getInt(1));
                bean.setCategory(new CategoryDAO().get(cid));
                bean.setName(rs.getString("name"));
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
