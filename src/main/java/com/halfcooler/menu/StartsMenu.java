package com.halfcooler.menu;

import com.halfcooler.Program;
import com.halfcooler.utils.ResourcesBundler;
import com.halfcooler.utils.SwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class StartsMenu extends JFrame
{
	private JPanel panel;
	private JButton difficultyEasyButton;
	private JButton difficultyNormalButton;
	private JButton difficultyHardButton;
	private JCheckBox musicCheckBox;
	private JComboBox<String> comboBox1;
	private JLabel fpsLabel;
	private JButton hallOfFameButton;

	private int difficulty = 0;
	private boolean musicOn = false;
	private int fps = 0;

	private final ResourcesBundler rb = new ResourcesBundler();

	public StartsMenu()
	{

		$$$setupUI$$$();

		this.difficultyEasyButton.addActionListener((ActionEvent ignored) ->
		{
			this.difficulty = 0;
			this.onButtonClicked();
		});

		this.difficultyNormalButton.addActionListener((ActionEvent ignored) ->
		{
			this.difficulty = 1;
			this.onButtonClicked();
		});

		this.difficultyHardButton.addActionListener((ActionEvent ignored) ->
		{
			this.difficulty = 2;
			this.onButtonClicked();
		});

		this.hallOfFameButton.addActionListener((ActionEvent ignored) ->
		{
			// TODO: Show Hall of Fame
			this.setVisible(false);
			HallOfFameMenu hof = new HallOfFameMenu();
			hof.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			hof.setTitle("Hall of Fame");
			hof.setSize(Program.WIDTH, Program.HEIGHT);
			hof.setVisible(true);
		});
	}

	public JPanel GetPanel()
	{
		return this.panel;
	}

	public int GetDifficulty()
	{
		return this.difficulty;
	}

	public boolean IsMusicOn()
	{
		return this.musicOn;
	}

	/// @return default 60
	/// @see DisplayMode#getRefreshRate()
	public int GetFps()
	{
		return this.fps == 0 ? 60 : this.fps;
	}

	private void onButtonClicked()
	{
		this.panel.setVisible(false);
		this.musicOn = musicCheckBox.isSelected();
		this.fps = "V-Sync".equals(this.comboBox1.getSelectedItem()) ?
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate() :
				Integer.parseInt((String) Objects.requireNonNull(this.comboBox1.getSelectedItem()));

		synchronized (Program.MainLock)
		{
			Program.MainLock.notify();
		}
	}

	private void $$$setupUI$$$()
	{
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		difficultyEasyButton = new JButton();
		SwingUtilities.LoadButtonText(difficultyEasyButton, rb.GetMessage("mode.easy"));
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		panel.add(difficultyEasyButton, gbc);
		difficultyNormalButton = new JButton();
		SwingUtilities.LoadButtonText(difficultyNormalButton, rb.GetMessage("mode.normal"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.5;
		panel.add(difficultyNormalButton, gbc);
		musicCheckBox = new JCheckBox();
		SwingUtilities.LoadButtonText(musicCheckBox, rb.GetMessage("mode.music"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		panel.add(musicCheckBox, gbc);
		difficultyHardButton = new JButton();
		SwingUtilities.LoadButtonText(difficultyHardButton, rb.GetMessage("mode.hard"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 0.5;
		panel.add(difficultyHardButton, gbc);
		comboBox1 = new JComboBox<>();
		final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
		defaultComboBoxModel1.addElement("V-Sync");
		defaultComboBoxModel1.addElement("45");
		defaultComboBoxModel1.addElement("60");
		defaultComboBoxModel1.addElement("90");
		defaultComboBoxModel1.addElement("120");
		comboBox1.setModel(defaultComboBoxModel1);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(comboBox1, gbc);
		fpsLabel = new JLabel();
		fpsLabel.setHorizontalAlignment(0);
		fpsLabel.setHorizontalTextPosition(0);
		SwingUtilities.LoadLabelText(fpsLabel, rb.GetMessage("mode.fps"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(fpsLabel, gbc);
		hallOfFameButton = new JButton();
		SwingUtilities.LoadButtonText(hallOfFameButton, rb.GetMessage("record.button"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weighty = 0.5;
		panel.add(hallOfFameButton, gbc);
	}

	public JComponent $$$getRootComponent$$$()
	{
		return panel;
	}

}
