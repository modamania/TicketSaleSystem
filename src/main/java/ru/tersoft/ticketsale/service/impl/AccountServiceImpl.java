package ru.tersoft.ticketsale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tersoft.ticketsale.entity.Account;
import ru.tersoft.ticketsale.repository.AccountRepository;
import ru.tersoft.ticketsale.repository.OrderRepository;
import ru.tersoft.ticketsale.service.AccountService;
import ru.tersoft.ticketsale.utils.ResponseFactory;

import java.util.List;
import java.util.UUID;

@Service("AccountService")
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;

    public Account findUserByMail(String mail) {
        List<Account> accounts = (List<Account>)accountRepository.findByMail(mail);
        if(accounts.size() != 0)
            return accounts.get(0);
        else
            return null;
    }

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OrderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> getAll(int pagenum, int limit) {
        return ResponseFactory.createResponse(accountRepository.findAll(new PageRequest(pagenum, limit)));
    }

    public ResponseEntity<?> get(UUID id) {
        Account account = accountRepository.findOne(id);
        if(account != null)
            return ResponseFactory.createResponse(account);
        else
            return ResponseFactory.createErrorResponse(HttpStatus.NOT_FOUND, "Account with such id was not found");

    }

    public ResponseEntity<?> add(Account account) {
        if(account != null) {
            account.setAdmin(false);
            int avatarNumber = account.getMail().length() % 10;
            account.setAvatar("/img/avatars/identicon"+avatarNumber+".png");
            if(account.isEnabled() == null) account.setEnabled(true);
            if(checkMail(account.getMail()))
                return ResponseFactory.createResponse(accountRepository.saveAndFlush(account));
            else
                return ResponseFactory.createErrorResponse(HttpStatus.BAD_REQUEST, "E-mail already in use");
        } else {
            return ResponseFactory.createErrorResponse(HttpStatus.BAD_REQUEST, "Passed empty account");
        }
    }

    public ResponseEntity<?> delete(UUID id) {
        Account account = accountRepository.findOne(id);
        if(account == null)
            return ResponseFactory.createErrorResponse(HttpStatus.NOT_FOUND, "Account with such id was not found");
        else {
            accountRepository.delete(id);
            return ResponseFactory.createResponse();
        }
    }

    public ResponseEntity<?> edit(Account account) {
        if(account == null)
            return ResponseFactory.createErrorResponse(HttpStatus.BAD_REQUEST, "Passed empty account");
        Account existingAccount = accountRepository.findOne(account.getId());
        if(existingAccount == null)
            return ResponseFactory.createErrorResponse(HttpStatus.NOT_FOUND, "Account with such id was not found");
        if(account.getFirstname() != null)
            existingAccount.setFirstname(account.getFirstname());
        if(account.getLastname() != null)
            existingAccount.setLastname(account.getLastname());
        if(account.getPassword() != null && !account.getPassword().isEmpty())
            existingAccount.setPassword(account.getPassword());
        if(account.getBirthdate() != null)
            existingAccount.setBirthdate(account.getBirthdate());
        if(account.isEnabled() != null)
            existingAccount.setEnabled(account.isEnabled());
        if(account.isAdmin() != null)
            existingAccount.setAdmin(account.isAdmin());
        return ResponseFactory.createResponse(accountRepository.saveAndFlush(existingAccount));
    }

    public ResponseEntity<?> changeFlags(UUID id, Boolean admin, Boolean enabled) {
        if(id == null)
            return ResponseFactory.createErrorResponse(HttpStatus.BAD_REQUEST, "Passed empty account id");
        Account account = accountRepository.findOne(id);
        if(account == null)
            return ResponseFactory.createErrorResponse(HttpStatus.NOT_FOUND, "Account with such id was not found");
        if(enabled != null)
            account.setEnabled(enabled);
        if(admin != null)
            account.setAdmin(admin);
        return ResponseFactory.createResponse(accountRepository.saveAndFlush(account));
    }

    @Override
    public Boolean checkMail(String mail) {
        List<Account> accountList = (List<Account>)accountRepository.findByMail(mail);
        return accountList.size() == 0;
    }
}
