package net.chance.cavernsofchaos.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class LeafParticles extends TextureSheetParticle {
    private final double rolldirection;

    protected LeafParticles(ClientLevel level, double xCoord, double yCoord, double zCoord,
                            SpriteSet spriteSet, double xd, double yd, double zd){
        super(level,xCoord,yCoord,zCoord,xd,yd,zd);
        this.friction = 1.0F;
        //this.xd = (Math.random()-0.5)*0.1;
        this.yd = yd;
        //this.zd = (Math.random()-0.5)*0.1;
        this.quadSize *= 0f; //0.85F;
        this.lifetime = 140;
        this.setSpriteFromAge(spriteSet);
        this.gravity = 1f;
        this.roll = (float) Math.random();
        this.rolldirection = Math.random()-0.5;
        this.age = 0;
        this.setSize(0.01f,0.01f);


        //this.rCol = 1f;
        //this.gCol = 1f;
        //this.bCol = 1f;

        
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick(){
        this.age++;
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.oRoll = this.roll;
        this.roll += 0.05*rolldirection;
        if(age<20){
            this.quadSize += 0.01f;
        }
        if(age>100&&this.quadSize>0.0f){
            this.quadSize -= 0.01f;
        }
        if(this.age>=this.lifetime){
            this.remove();
        }

        this.move(this.xd*(this.age/80.0),this.yd,this.zd*(this.age/80.0));
        //this.roll += (0.01f);
        this.yd -= 0.0001;
    }



    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
                                       double pXSpeed, double pYSpeed, double pZSpeed) {
            return new LeafParticles(pLevel, pX, pY, pZ, this.spriteSet, pXSpeed, -0.1f, pZSpeed);
        }
    }
}