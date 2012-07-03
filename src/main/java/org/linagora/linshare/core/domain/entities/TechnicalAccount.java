package org.linagora.linshare.core.domain.entities;

import org.linagora.linshare.core.domain.constants.AccountType;

public class TechnicalAccount extends User {

	@Override
	public AccountType getAccountType() {
		return AccountType.TECHNICALACCOUNT;
	}
	
	@Override
	public String getAccountReprentation() {
//		return this.firstName + " " + this.lastName + "(" + lsUid + ")";
		return lsUid ;
	}


}
