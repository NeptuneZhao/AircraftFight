package com.halfcooler.game;

import java.awt.image.BufferedImage;

import com.halfcooler.utils.ImageManager;

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
