package org.pismery.javacourse.hmily.account.api;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.pismery.javacourse.hmily.account.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@DubboService
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    private final TransactionService transactionService;


    @Autowired(required = false)
    public AccountServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean paySuccess(Account account) {
        log.info("============ dubbo tcc 执行 Try paySuccess===============");
        Account original = accountMapper.queryOne(account);

        original.setCnyWallet(original.getCnyWallet() + account.getCnyWallet());
        original.setUsWallet(original.getUsWallet() + account.getUsWallet());

        accountMapper.payment(account);
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancelException")
    public boolean payException() {
        log.info("============ dubbo tcc 执行 Try payException===============");
        throw new RuntimeException("payException");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(Account account) {
        log.info("============ dubbo tcc 执行确认付款接口===============");
        log.info("param account : " + account.toString());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Account account) {
        log.info("============ dubbo tcc 执行取消付款接口===============");
        log.info("param account : " + account.toString());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancelException(Account account) {
        log.info("============ dubbo tcc 执行 cancelException 接口===============");
        log.info("param account : " + account.toString());
        return true;
    }
}
