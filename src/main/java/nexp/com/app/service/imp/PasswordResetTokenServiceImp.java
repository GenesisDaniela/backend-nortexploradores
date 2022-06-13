package nexp.com.app.service.imp;

import nexp.com.app.dao.PasswordResetTokenDAO;
import nexp.com.app.model.PasswordResetToken;
import nexp.com.app.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class PasswordResetTokenImp implements PasswordResetTokenService {

    @Autowired
    PasswordResetTokenDAO passwordResetTokenDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PasswordResetToken> listar() {
        return passwordResetTokenDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordResetToken buscarToken(String token) {
        return passwordResetTokenDAO.findPasswordResetTokenByToken(token);
    }

    @Override
    @Transactional
    public void guardar(PasswordResetToken ct) {
        passwordResetTokenDAO.save(ct);
    }

    @Override
    @Transactional
    public void eliminar(PasswordResetToken ct) {
        passwordResetTokenDAO.delete(ct);
    }

    @Override
    @Transactional
    public void eliminarByToken(String ct) {
        passwordResetTokenDAO.deletePasswordResetTokenByToken(ct);
    }
}