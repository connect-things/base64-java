package br.eti.francisco.base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;




public class Base64ApiJava {
	public static void main(String[] args) {
		try {
//			String text = "test test test test";
			String text = "te!@#á";
			String base64 = Base64.encodeBase64URLSafeString(text.getBytes("UTF-8"));
			System.out.println(base64);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
