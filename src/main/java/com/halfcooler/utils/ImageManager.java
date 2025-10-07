package com.halfcooler.utils;

import com.halfcooler.flying.bullet.BulletEnemy;
import com.halfcooler.flying.bullet.BulletHero;
import com.halfcooler.flying.prop.PropBomb;
import com.halfcooler.flying.prop.PropBullet;
import com.halfcooler.flying.prop.PropHealth;
import com.halfcooler.flying.warplane.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 写完了!
public final class ImageManager
{
	private static final Map<String, BufferedImage> Map = new HashMap<>();

	public static final BufferedImage
		BackgroundImg;
	public static final BufferedImage HeroImg;
	public static final BufferedImage BulletHeroImg;
	public static final BufferedImage EnemyImg;
	public static final BufferedImage BulletEnemyImg;
	public static final BufferedImage EliteImg;
	public static final BufferedImage PlusImg;
	public static final BufferedImage BossImg;
	public static final BufferedImage PropBombImg;
	public static final BufferedImage PropBulletImg;
	public static final BufferedImage PropHealthImg;

	static
	{
		try
		{
			BackgroundImg = ImageIO.read(new FileInputStream("src/main/resources/images/bg.jpg"));

			HeroImg = ImageIO.read(new FileInputStream("src/main/resources/images/hero.png"));
			BulletHeroImg = ImageIO.read(new FileInputStream("src/main/resources/images/bullet_hero.png"));

			EnemyImg = ImageIO.read(new FileInputStream("src/main/resources/images/enemy.png"));
			BulletEnemyImg = ImageIO.read(new FileInputStream("src/main/resources/images/bullet_enemy.png"));

			EliteImg = ImageIO.read(new FileInputStream("src/main/resources/images/elite.png"));
			PlusImg = ImageIO.read(new FileInputStream("src/main/resources/images/plus.png"));
			BossImg = ImageIO.read(new FileInputStream("src/main/resources/images/boss.png"));

			PropBombImg = ImageIO.read(new FileInputStream("src/main/resources/images/prop_bomb.png"));
			PropBulletImg = ImageIO.read(new FileInputStream("src/main/resources/images/prop_bullet.png"));
			PropHealthImg = ImageIO.read(new FileInputStream("src/main/resources/images/prop_health.png"));

			Map.put(WarplaneHero.class.getName(), HeroImg);
			Map.put(BulletHero.class.getName(), BulletHeroImg);

			Map.put(WarplaneEnemy.class.getName(), EnemyImg);
			Map.put(BulletEnemy.class.getName(), BulletEnemyImg);

			Map.put(WarplaneElite.class.getName(), EliteImg);
			Map.put(WarplanePlus.class.getName(), PlusImg);
			Map.put(WarplaneBoss.class.getName(), BossImg);

			Map.put(PropBomb.class.getName(), PropBombImg);
			Map.put(PropBullet.class.getName(), PropBulletImg);
			Map.put(PropHealth.class.getName(), PropHealthImg);

		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static BufferedImage GetImage(String className)
	{
		return Map.get(className);
	}

	public static BufferedImage GetImage(Object e)
	{
		return e == null ? null : GetImage(e.getClass().getName());
	}
}
