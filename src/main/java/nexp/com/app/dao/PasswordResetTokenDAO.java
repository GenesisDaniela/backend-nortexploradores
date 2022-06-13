package nexp.com.app.dao;

import nexp.com.app.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordResetTokenDAO extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findPasswordResetTokenByToken(String token);
    @Modifying
    @Query("delete from PasswordResetToken prt where prt.token=:token")
    void deletePasswordResetTokenByToken(@Param("token") String token);
}

