package com.halfcooler.utils;

import com.halfcooler.flying.bullet.*;
import com.halfcooler.flying.warplane.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ImageManager
{
	private static final Map<String, BufferedImage> Map = new HashMap<>();

	public static BufferedImage BackgroundImg, HeroImg, HeroBulletImg, EnemyImg, EnemyBulletImg;

	static
	{
		try
		{
			BackgroundImg = ImageIO.read(new FileInputStream("src/main/resources/images/bg.png"));
			HeroImg = ImageIO.read(new FileInputStream("src/main/resources/images/hero.png"));
			HeroBulletImg = ImageIO.read(new FileInputStream("src/main/resources/images/hero_bullet.png"));
			EnemyImg = ImageIO.read(new FileInputStream("src/main/resources/images/enemy.png"));
			EnemyBulletImg = ImageIO.read(new FileInputStream("src/main/resources/images/enemy_bullet.png"));

			Map.put(WarplaneHero.class.getName(), HeroImg);
			Map.put(WarplaneEnemy.class.getName(), EnemyImg);
			Map.put(BulletHero.class.getName(), HeroBulletImg);
			Map.put(BulletEnemy.class.getName(), EnemyBulletImg);

		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static BufferedImage getImage(String className)
	{
		return Map.get(className);
	}

	public static BufferedImage getImage(Object e)
	{
		return e == null ? null : getImage(e.getClass().getName());
	}
}
