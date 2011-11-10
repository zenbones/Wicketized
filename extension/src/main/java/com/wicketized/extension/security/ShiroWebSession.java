package com.wicketized.extension.security;

import java.util.LinkedList;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class ShiroWebSession extends AuthenticatedWebSession {

  private static final Roles NO_ROLES = new Roles();

  public ShiroWebSession (Request request) {

    super(request);
  }

  @Override
  public boolean authenticate (String username, String password) {

    Subject currentUser;

    if (!(currentUser = SecurityUtils.getSubject()).isAuthenticated()) {

      UsernamePasswordToken token = new UsernamePasswordToken(username, password);

      token.setRememberMe(true);
      try {
        currentUser.login(token);
      }
      catch (Exception exception) {

        return false;
      }
    }

    return true;
  }

  @Override
  public Roles getRoles () {

    Subject subject;

    if (((subject = SecurityUtils.getSubject()) != null) && subject.isAuthenticated()) {

      LinkedList<String> codeList;
      String[] codes;

      codeList = new LinkedList<String>();
      for (RoleType roleType : RoleType.values()) {
        if (subject.hasRole(roleType.getCode())) {
          codeList.add(roleType.getCode());
        }
      }

      codes = new String[codeList.size()];
      codeList.toArray(codes);

      return new Roles(codes);
    }

    return NO_ROLES;
  }

  @Override
  public void signOut () {

    SecurityUtils.getSubject().logout();
    super.signOut();
  }
}
