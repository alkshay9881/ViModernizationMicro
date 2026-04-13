package com.mobicule.authserver.repository;

import com.mobicule.authserver.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public UserRepository(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;

        log.info("UserRepository initialized");
        log.info("JDBC URL: {}", jdbcUrl);
        log.info("DB User: {}", dbUser);
        log.info("DB Password configured: {}", dbPassword != null);
    }

    // -------------------- FIND BY MOBILE --------------------

    public UserDetails findByMobile(String mobile) {

        log.info("Finding user by mobile: {}", mobile);

        String query =
                "SELECT mobile_number, login_password " +
                        "FROM user_details " +
                        "WHERE mobile_number=? AND delete_flag='F'";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, mobile);

            log.info("Executing query: findByMobile");

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    UserDetails user = new UserDetails();
                    user.setMobNo(rs.getString("mobile_number"));
                    user.setLoginPassword(rs.getString("login_password"));

                    log.info("User found for mobile: {}", mobile);
                    return user;
                }
            }

        } catch (SQLException e) {
            log.error("SQL error in findByMobile for mobile: {}", mobile, e);
            throw new RuntimeException("Database error in findByMobile", e);
        } catch (Exception e) {
            log.error("Unexpected error in findByMobile for mobile: {}", mobile, e);
            throw new RuntimeException("Unexpected error in findByMobile", e);
        }

        log.warn("User not found for mobile: {}", mobile);
        return null;
    }

    // -------------------- VALIDATE CREDENTIALS --------------------

    public boolean validateCredentials(String mobile, String password) {

        log.info("Validating credentials for mobile: {}", mobile);

        UserDetails user = findByMobile(mobile);

        if (user == null) {
            log.warn("Validation failed - user not found: {}", mobile);
            return false;
        }

        boolean valid = password != null && password.equals(user.getLoginPassword());

        log.info("Credential validation result for {}: {}", mobile, valid);

        return valid;
    }

    // -------------------- SEARCH (ORACLE FIXED) --------------------

    public List<UserDetails> searchByMobile(String search, int first, int max) {

        log.info("Search called with search={}, first={}, max={}", search, first, max);

        String sql =
                "SELECT mobile_number, login_password " +
                        "FROM user_details " +
                        "WHERE delete_flag='F' " +
                        "AND mobile_number LIKE ? " +
                        "ORDER BY mobile_number " +
                        "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<UserDetails> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + search + "%");
            ps.setInt(2, first);
            ps.setInt(3, max);

            log.info("Executing Oracle pagination query");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    UserDetails u = new UserDetails();
                    u.setMobNo(rs.getString("mobile_number"));
                    u.setLoginPassword(rs.getString("login_password"));
                    list.add(u);
                }
            }

            log.info("Search result size: {}", list.size());

        } catch (SQLException e) {
            log.error("SQL error in searchByMobile (search={})", search, e);
            throw new RuntimeException("Database error in searchByMobile", e);
        } catch (Exception e) {
            log.error("Unexpected error in searchByMobile (search={})", search, e);
            throw new RuntimeException("Unexpected error in searchByMobile", e);
        }

        return list;
    }

    // -------------------- FIND ALL (ORACLE FIXED) --------------------

    public List<UserDetails> findAll(int first, int max) {

        log.info("findAll called with first={}, max={}", first, max);

        String sql =
                "SELECT mobile_number, login_password " +
                        "FROM user_details " +
                        "WHERE delete_flag='F' " +
                        "ORDER BY mobile_number " +
                        "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<UserDetails> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, first);
            ps.setInt(2, max);

            log.info("Executing Oracle findAll query");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    UserDetails u = new UserDetails();
                    u.setMobNo(rs.getString("mobile_number"));
                    u.setLoginPassword(rs.getString("login_password"));
                    list.add(u);
                }
            }

            log.info("findAll result size: {}", list.size());

        } catch (SQLException e) {
            log.error("SQL error in findAll", e);
            throw new RuntimeException("Database error in findAll", e);
        } catch (Exception e) {
            log.error("Unexpected error in findAll", e);
            throw new RuntimeException("Unexpected error in findAll", e);
        }

        return list;
    }
}