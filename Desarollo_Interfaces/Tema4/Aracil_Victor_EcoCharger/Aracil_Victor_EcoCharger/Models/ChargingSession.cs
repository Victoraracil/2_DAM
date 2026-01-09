using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Aracil_Victor_EcoCharger.Models
{

    public class ChargingSession
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int ChargerId { get; set; }

        [Required]
        public int UserId { get; set; }

        [Required]
        public TimeSpan StartTime { get; set; }

        public TimeSpan? EndTime { get; set; }

        [Column(TypeName = "decimal(18, 2)")]
        public decimal KWhConsumed { get; set; }

        [Column(TypeName = "decimal(18, 4)")]
        public decimal TotalCost { get; set; }

        //Relaciones

        [ForeignKey("UserId")]
        public User Users { get; set; }


        [ForeignKey("ChargerId")]
        public Charger SesionAsociada { get; set; }
    }


}
