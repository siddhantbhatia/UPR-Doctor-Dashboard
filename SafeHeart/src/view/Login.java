package view;

import javax.swing.JPanel;
import javax.swing.JTextField;

import user.Clinician;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JPanel {
	private JTextField txtPractitionerId;

	/**
	 * Create the panel.
	 */
	public Login() {
		setLayout(null);
		
		txtPractitionerId = new JTextField();
		txtPractitionerId.setText("Practitioner ID");
		txtPractitionerId.setBounds(68, 93, 130, 26);
		add(txtPractitionerId);
		txtPractitionerId.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.login(txtPractitionerId.getText());
			}
		});
		btnLogin.setBounds(236, 93, 117, 29);
		add(btnLogin);

	}

}
