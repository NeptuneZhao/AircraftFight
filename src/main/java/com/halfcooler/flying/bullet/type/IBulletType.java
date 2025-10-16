package com.halfcooler.flying.bullet.type;

import com.halfcooler.flying.bullet.Bullet;
import com.halfcooler.flying.warplane.Warplane;

import java.util.List;

/// 能想出来策略模式的家里得请高人了<br>
/// 纯种傻逼吧<br>
/// 有病<br>
/// 绕他妈一大圈, 最后不如我一个静态方法来的实在
public interface IBulletType
{
	List<? extends Bullet> Shoots(Warplane enemy);
}
