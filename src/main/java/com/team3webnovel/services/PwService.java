package com.team3webnovel.services;

public interface PwService {
	String getGoogleAppPassword();

	String decryptPassword(String encryptedPassword);
}
