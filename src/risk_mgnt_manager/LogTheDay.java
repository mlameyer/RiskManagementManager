/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import javax.swing.JTextArea;

/**
 *
 * @author bgilbert
 */
class LogTheDay {
    private JTextArea j;

    LogTheDay(JTextArea j) {
        this.j = j;
    }

    void execute() {

        ActivityLogger act = new ActivityLogger(j);
        act.logRecords();

    } 
}
