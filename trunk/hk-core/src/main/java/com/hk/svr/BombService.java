package com.hk.svr;

import java.util.List;
import com.hk.bean.BombLaba;
import com.hk.bean.Bomber;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.PinkLaba;
import com.hk.svr.bomb.exception.NotEnoughBombException;
import com.hk.svr.bomb.exception.NotEnoughPinkException;

public interface BombService {
	void createBomber(long adminUserId, long userId, int bombCount,
			byte userLevel, int pinkCount) throws NotEnoughBombException,
			NotEnoughPinkException;

	void deleteBomber(long userId);

	List<Bomber> getBomberList(int begin, int size);

	Bomber getBomber(long userId);

	// boolean login(long userId, String pwd);
	void addBomb(long adminUserId, long userId, int add)
			throws NotEnoughBombException;

	void addPink(long adminUserId, long userId, int add)
			throws NotEnoughPinkException;

	// /**
	// * @param userId 炸弹使用人
	// * @param labaId
	// * @return
	// */
	// LabaDelInfo bombLaba(long userId, long labaId);
	List<BombLaba> getBombLabaList(long userId, int begin, int size);

	List<BombLaba> getBombLabaList(int begin, int size);

	BombLaba getBombLaba(long sysId);

	// boolean updatePwd(long userId, String oldPwd, String newPwd);
	boolean pinkLaba(long userId, long labaId);

	void addRemainPinkCount(long userId, int add);

	void addRemainBombCount(long userId, int add);

	List<PinkLaba> getPinkLabaList(long userId, int begin, int size);

	List<PinkLaba> getPinkLabaList(int begin, int size);

	PinkLaba getPinkLaba(long labaId);

	void deletePinkLaba(long labaId);

	boolean hasBombLaba(long labaId);

	BombLaba createBombLaba(long userId, long labaId, LabaDelInfo labaDelInfo);
}