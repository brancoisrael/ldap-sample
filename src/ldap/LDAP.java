package ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import model.Usuario;

public class LDAP {

	private static final String LDAP_ATTRIBUTE_USER_PASSWORD = "userPassword";
	private static final String LDAP_ATTRIBUTE_CN = "cn";
	private static final String LDAP_ATTRIBUTE_MAIL = "mail";
	private static final String LDAP_ATTRIBUTE_UID = "uid";
	private static final String LDAP_ATTRIBUTE_SN = "sn";

	private static final List<String> objectClassList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

		{
			add("top");
			add("organizationalperson");
			add("inetorgperson");
			add("person");
			add("usersecurityinformation");
		}
	};

	private LdapContext managerContext;

	public LDAP() throws Exception {
		Hashtable<String, Object> userEnv = new Hashtable<String, Object>();

		String principalName = "cn=angelo,dc=osradar,dc=local";
		userEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		userEnv.put(Context.PROVIDER_URL, "ldap://10.10.4.77:389/dc=osradar,dc=local");
		userEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		userEnv.put(Context.SECURITY_PRINCIPAL, principalName);
		userEnv.put(Context.SECURITY_CREDENTIALS, "123456");

		managerContext = new InitialLdapContext(userEnv, null);

	}

	public void inserir(Usuario usuario) throws Exception {

		Attributes entry = new BasicAttributes();
		entry.put(new BasicAttribute(LDAP_ATTRIBUTE_CN, usuario.getCn()));
		entry.put(new BasicAttribute(LDAP_ATTRIBUTE_SN, usuario.getSn()));
		entry.put(new BasicAttribute(LDAP_ATTRIBUTE_MAIL, usuario.getEmail()));
		entry.put(new BasicAttribute(LDAP_ATTRIBUTE_USER_PASSWORD, usuario.getUserPassword()));
		entry.put(new BasicAttribute(LDAP_ATTRIBUTE_UID, usuario.getUid()));

		Attribute oc = new BasicAttribute("objectClass");
		objectClassList.forEach(ca -> oc.add(ca));
		entry.put(oc);

		managerContext.createSubcontext("uid=" + usuario.getUid() + ",ou=identidades,o=sisdepen", entry);

	}
}
