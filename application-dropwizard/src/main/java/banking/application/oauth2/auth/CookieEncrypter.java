/*
 *  Copyright (C) 2015  oauth2-dropwizard project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package banking.application.oauth2.auth;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import banking.application.oauth2.apifest.AccessToken;
import banking.application.oauth2.apifest.CookieToken;

/**
 * Encrypts/decrypts the authentication cookie stored on client's browser.
 *
 * @author Edouard De Oliveira
 */
public class CookieEncrypter {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int BIT_LENGTH = 128; // 192 and 256 bits may not be available

    private transient SecretKeySpec keySpec;
    private ObjectMapper mapper = new ObjectMapper();
    private boolean secureFlag;

    public CookieEncrypter() throws Exception {
        // Get the KeyGenerator
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        kgen.init(BIT_LENGTH); // 192 and 256 bits may not be available

        // Generate the secret key specs
        SecretKey skey = kgen.generateKey();
        byte[] secretKey = skey.getEncoded();
        keySpec = new SecretKeySpec(secretKey, ALGORITHM);
    }

    public CookieEncrypter(String secret) {
        byte[] tmp = secret.getBytes(UTF8);

        if ((tmp.length * 8) < BIT_LENGTH)
            throw new IllegalArgumentException("Wrong key size (" + (tmp.length * 8) + ") lower than the " + BIT_LENGTH + " bits required");

        byte[] secretKey = new byte[BIT_LENGTH / 8];
        System.arraycopy(tmp, 0, secretKey, 0, secretKey.length);
        keySpec = new SecretKeySpec(secretKey, ALGORITHM);
    }

    public void setSecureFlag(boolean secureFlag) {
        this.secureFlag = secureFlag;
    }

    private String encode(String content) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] output = cipher.doFinal(content.getBytes(UTF8));
        return Base64.encodeBase64String(output);
    }

    private String decode(String content) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] output = cipher.doFinal(Base64.decodeBase64(content));
        return new String(output, UTF8);
    }

    protected NewCookie buildCookie(String username, AccessToken token, String domain)
            throws Exception {
        CookieToken ct = new CookieToken(token, username);
        String value = mapper.writeValueAsString(ct);
        value = encode(value);
        int maxAge = Integer.parseInt(token.getExpiresIn());
        return new NewCookie(OAuth2AuthFilter.AUTH_COOKIE_NAME,
                value, "/", domain, null, maxAge, secureFlag, true);
    }

    protected CookieToken readCookie(Cookie cookie) throws Exception {
        String json = decode(cookie.getValue());
        return mapper.readValue(json, CookieToken.class);
    }

}
