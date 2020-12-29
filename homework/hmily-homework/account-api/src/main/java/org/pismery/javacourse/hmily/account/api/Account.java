package org.pismery.javacourse.hmily.account.api;

import java.io.Serializable;

public class Account implements Serializable {
    private Long id;
    private String name;
    private Long cnyWallet;
    private Long usWallet;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCnyWallet() {
        return cnyWallet;
    }
    public void setCnyWallet(Long cnyWallet) {
        this.cnyWallet = cnyWallet;
    }
    public Long getUsWallet() {
        return usWallet;
    }
    public void setUsWallet(Long usWallet) {
        this.usWallet = usWallet;
    }
}
