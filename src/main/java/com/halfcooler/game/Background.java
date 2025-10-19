package com.halfcooler.game;

import com.halfcooler.game.utils.ImageManager;

import java.awt.image.BufferedImage;

public record Background(BufferedImage backgroundImg)
{
	public Background(int df)
	{
		this (switch (df)
		{
			case 0 -> ImageManager.BgEasyImg;
			case 1 -> ImageManager.BgNormalIMg;
			case 2 -> ImageManager.BgHardImg;
			default -> throw new IllegalArgumentException("Invalid difficulty");
		});
	}

}
