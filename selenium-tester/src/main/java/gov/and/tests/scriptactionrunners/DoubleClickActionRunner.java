/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.and.tests.scriptactionrunners;

import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import gov.and.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import gov.and.tests.actions.TestAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * doble clic en algun lado.
 * @author nesto
 */
public class DoubleClickActionRunner extends AbstractCssSelectorActionRunner{

    public DoubleClickActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public String getActionName() {
        return "doble clic";
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        Actions actions = new Actions(driver);
        actions.doubleClick(get(driver));
    }
    
}
