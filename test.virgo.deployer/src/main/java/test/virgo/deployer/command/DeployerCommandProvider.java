package test.virgo.deployer.command;

import org.eclipse.virgo.nano.deployer.api.core.ApplicationDeployer;

public class DeployerCommandProvider {
   private ApplicationDeployer deployer;

   public void setDeployer( ApplicationDeployer deployer ) {
      this.deployer = deployer;
   }

   public void deploy( String[] args ) {
      // String sql = StringUtils.arrayToDelimitedString(args, " ");
      //       jdbcTemplate.execute(sql);
      System.out.println( "Deployer: " + deployer );
   }
}
