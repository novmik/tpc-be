package com.novmik.tpc.refreshtoken;

import com.novmik.tpc.client.Client;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.NaturalId;

/**
 * RefreshToken entity class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

  /**
   * id RefreshToken.
   */
  @Id
  @Column(name = "token_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
  @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
  private Long idRefreshToken;

  /**
   * Токен.
   */
  @Column(name = "token", nullable = false, unique = true)
  @NaturalId(mutable = true)
  private String token;
  /**
   * Пользователь {@link Client}.
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  @Exclude
  private Client client;
  /**
   * Счётчик обновления токена.
   */
  @Column(name = "refresh_count")
  private Long refreshCount;

  /**
   * Instant истечения срока действия токена.
   */
  @Column(name = "expiry_date", nullable = false)
  private Instant expiryDate;

  /**
   * Увеличение счётчика обновления токена.
   */
  public void incrementRefreshCount() {
    refreshCount = refreshCount + 1;
  }

}
