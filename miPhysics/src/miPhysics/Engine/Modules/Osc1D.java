package miPhysics;

public class Osc1D extends Mat {

	public Osc1D(double M, double K_param, double Z_param, Vect3D initPos, Vect3D initPosR, double friction, Vect3D grav) {
		super(M, initPos, initPosR);
		setType(matModuleType.Osc1D);

		m_pRest = initPos.z;

		m_K = K_param;
		m_Z = Z_param;

		m_A = 2. - m_invMass * m_K - m_invMass * (m_Z+m_fricZ) ;
		m_B = 1. -m_invMass * (m_fricZ + m_Z) ;
		
		m_fricZ = friction;
		m_gFrc = new Vect3D();
		m_gFrc.set(grav);
	}

	public void compute() {

		m_pos.z -= m_pRest;
		m_posR.z -= m_pRest;

		newPos = m_A * m_pos.z - m_B * m_posR.z + m_frc.z * m_invMass;
		m_posR.z = m_pos.z;
		m_pos.z = newPos;
		m_frc.set(0., 0., 0.);

		m_pos.z += m_pRest;
		m_posR.z += m_pRest;

	}

	public void updateGravity(Vect3D grav) {
		m_gFrc.set(grav);
	}
	public void updateFriction(double fric) {
		m_fricZ= fric;

		m_A = 2. - m_invMass * m_K - m_invMass * (m_Z+m_fricZ) ;
		m_B = 1. -m_invMass * (m_fricZ + m_Z) ;
	}

	public double distRest() {  // AJOUT JV Permet de sortir le DeltaX relatif entre mas et sol d'une cel
		return (m_pos.z - m_pRest);
	}

	/* Class attributes */

	private double m_A;
	private double m_B;

	private double m_K;
	private double m_Z;
	private double m_fricZ;
	private Vect3D m_gFrc;

	double m_pRest;
	double newPos;
}
